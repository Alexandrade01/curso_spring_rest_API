package curso.api.rest.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import curso.api.rest.ApplicationContextLoad;
import curso.api.rest.model.Cliente;
import curso.api.rest.repository.ClienteRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JWTTokenAutenticacaoService {

	/* Validade de 3 dias em milissegundos */
	private static final long EXPIRATION_TIME = 259200000L;

	/*
	 * Uma senha única para autenticação. Recomenda-se guardar em Base64 em
	 * application.properties. Exemplo: security.jwt.secret=Base64DaSuaChave
	 *
	 * Se você optar por manter como texto puro, garanta pelo menos 32 bytes para
	 * HS256.
	 */
	private static final String SECRET_BASE64 = null; // <-- preencha se for Base64
	private static final String SECRET_RAW = "SenhaSecretaSenhaSecretaSenhaSecreta";

	/* Prefixo padrão do token */
	private static final String TOKEN_PREFIX = "Bearer";
	private static final String HEADER_STRING = "Authorization";

	/* Chave HMAC (HS256). Preferir Base64 quando possível */
	private final SecretKey signingKey;

	public JWTTokenAutenticacaoService() {
		if (SECRET_BASE64 != null && !SECRET_BASE64.isBlank()) {
			this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_BASE64));
		} else {
			// Uso de texto puro (evite em produção; prefira Base64)
			this.signingKey = Keys.hmacShaKeyFor(SECRET_RAW.getBytes(java.nio.charset.StandardCharsets.UTF_8));
		}
	}

	/* Gera Token de autenticação e adiciona ao cabeçalho e à resposta HTTP */
	public void addAuthentication(HttpServletResponse response, String username) throws IOException {

		String jwt = Jwts.builder().subject(username) // API nova 0.12.x
				.issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(signingKey, Jwts.SIG.HS256) // signWith(Key[, alg])
				.compact();

		// Junta token com prefixo
		String token = TOKEN_PREFIX + " " + jwt; // bearer (jwt)

		// Adiciona no cabeçalho http
		response.addHeader(HEADER_STRING, token);

		// Escreve token no corpo http (JSON simples)
		response.setContentType("application/json");
		response.getWriter().write("{\"Authorization\":\"" + token + "\"}");
	}

	/* Retorna o usuario validado com o token, caso nao seja valido retorna null */
	public Authentication getAuthenticaton(HttpServletRequest request) {
		// Pega o token enviado no cabeçalho HTTP
		String token = request.getHeader(HEADER_STRING);

		if (token == null || token.isBlank()) {

			return null;

		}

		// Espera "Bearer <token>"
		String prefix = TOKEN_PREFIX + " ";
		if (!token.startsWith(prefix)) {
			return null;
		}

		String jwt = token.substring(prefix.length()).trim();

		try {
			// Valida assinatura e extrai Claims (API nova 0.12.x)
			Claims claims = Jwts.parser().verifyWith(signingKey) // substitui setSigningKey(...) /* bearer
																	// 98474744ssssd9w9384338 */
					.build().parseSignedClaims(jwt) // substitui parseClaimsJws(...) /* 98474744ssssd9w9384338 */
					.getPayload();

			String user = claims.getSubject(); // substitui getBody() /*joao silva*/

			if (user != null) {
				Cliente cliente = ApplicationContextLoad.getApplicationContext() // Applicationcontext são todos os
																					// services , controles etc
																					// carregados em memoria na
																					// aplicacao
						.getBean(ClienteRepository.class).findUserByLogin(user);

				// retorna o usuario logado
				if (cliente != null) {

					return new UsernamePasswordAuthenticationToken(cliente.getUsername(), cliente.getSenha(),
							cliente.getAuthorities());

				} else {
					return null;
				}

			} else {
				return null;
			}

		} catch (Exception ex) {
			// Pode ser assinatura inválida, token expirado, etc.
			return null;
		}

	}

}