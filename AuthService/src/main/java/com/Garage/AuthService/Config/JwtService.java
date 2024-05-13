package com.Garage.AuthService.Config;


import com.Garage.AuthService.User.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "JsNVm2juYWzy78yatVOCZUnttbBxKjIuZmAuO/PMnaY+VM2nT6F8Z6N3MGaULriP";
    private final Set<String> invalidatedTokens = new HashSet<>();
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    public ResponseEntity<?> checkEmailExists(String email){
        if(userRepository.findByEmail(email).isEmpty()){
            return ResponseEntity.ok().body(null);
        }
        return ResponseEntity.badRequest().body(null);
    }
    public String extractUserEmail(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public String generateToken(
            Map<String,Object> extraClaims,
            UserDetails userDetails
    ){
        extraClaims.put("role",userDetails.getAuthorities());
        extraClaims.put("email", userDetails.getUsername());


        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generatePasswordResetToken(String userEmail) {
        Map<String, Object> claims = new HashMap<>();
        // Set expiration time to 30 minutes
        long expirationTime = System.currentTimeMillis() + 30 * 60 * 1000; // 30 minutes
        claims.put("email", userEmail);
        claims.put("expiration", expirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public <T>T extractClaim(String jwtToken, Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(jwtToken);
        return claimResolver.apply(claims);
    }
    public void invalidateToken(String jwtToken) {
        invalidatedTokens.add(jwtToken);
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }



    public boolean isTokenValid(String jwtToken,UserDetails userDetails){
        final String userName = extractUserEmail(jwtToken);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken,Claims::getExpiration);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
//    public String extractUserEmailFromToken(HttpServletRequest request) {
//        String token = extractTokenFromHeader(request);
//        if (token != null) {
//            return extractUserEmail(token);
//        }
//        return null;
//    }

//    private String extractTokenFromHeader(HttpServletRequest request) {
//        final String authHeader = request.getHeader("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            return authHeader.substring(7);
//        }
//        return null;
//    }

    public ResponseEntity<String> validateToken(@NonNull HttpServletRequest request){
        try{
            final String authHeader = request.getHeader("Authorization");
            String jwtToken = authHeader.substring(7);

            extractUserEmail(jwtToken);

            final String userEmail = extractUserEmail(jwtToken);
            if(userEmail != null || SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if(isTokenValid(jwtToken,userDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                return ResponseEntity.ok(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().get());
            }
            return ResponseEntity.badRequest().body("Token is invalid");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Token is invalid");
        }
    }
}
