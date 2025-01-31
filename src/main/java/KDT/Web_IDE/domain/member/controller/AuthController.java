package KDT.Web_IDE.domain.member.controller;

import org.springframework.web.bind.annotation.*;

import KDT.Web_IDE.domain.member.dto.request.LoginMemberRequestDto;
import KDT.Web_IDE.domain.member.dto.request.ReissueRequestDto;
import KDT.Web_IDE.domain.member.dto.request.SignUpMemberRequestDto;
import KDT.Web_IDE.domain.member.dto.response.TokenResponseDto;
import KDT.Web_IDE.domain.member.service.auth.facade.AuthFacade;
import KDT.Web_IDE.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthFacade authFacade;

  @PostMapping("/sign")
  public BaseResponse<Void> signUpMember(@RequestBody SignUpMemberRequestDto requestDto) {
    authFacade.signUpMember(requestDto);
    return BaseResponse.onSuccess(null);
  }

  @PostMapping("/login")
  public BaseResponse<TokenResponseDto> loginMember(@RequestBody LoginMemberRequestDto requestDto) {
    return BaseResponse.onSuccess(authFacade.loginMember(requestDto));
  }

  @PostMapping("/reissue")
  public BaseResponse<TokenResponseDto> reissue(@RequestBody ReissueRequestDto requestDto) {
    return BaseResponse.onSuccess(authFacade.reissue(requestDto));
  }
}
