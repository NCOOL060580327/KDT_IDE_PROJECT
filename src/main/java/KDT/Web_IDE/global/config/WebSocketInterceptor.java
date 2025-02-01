package KDT.Web_IDE.global.config;

import jakarta.annotation.PostConstruct;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.custom.AuthException;
import KDT.Web_IDE.global.security.provider.JwtProvider;
import KDT.Web_IDE.global.security.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebSocketInterceptor implements ChannelInterceptor {

  private final JwtProvider jwtProvider;
  private final MemberDetailsService memberDetailsService;

  @PostConstruct
  public void init() {
    SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
  }

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {

    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

    if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
      return message;
    }

    String authHeader = accessor.getFirstNativeHeader("Authorization");

    if (jwtProvider.isValidToken(authHeader)) {

      Long memberId = jwtProvider.getSubject(authHeader);

      UserDetails userDetails = memberDetailsService.loadUserByUsername(memberId.toString());

      Authentication authentication =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authentication);

      accessor.setUser(authentication);

    } else {
      throw new AuthException(GlobalErrorCode.INVALID_TOKEN);
    }

    return message;
  }
}
