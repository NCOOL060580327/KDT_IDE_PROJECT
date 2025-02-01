package KDT.Web_IDE.domain.board;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import KDT.Web_IDE.domain.board.dto.request.CreateBoardRequestDto;
import KDT.Web_IDE.domain.board.dto.request.UpdateBoardRequestDto;
import KDT.Web_IDE.domain.board.dto.response.GetBoardResponseDto;
import KDT.Web_IDE.domain.board.facade.BoardFacade;
import KDT.Web_IDE.global.response.BaseResponse;
import KDT.Web_IDE.global.security.domain.MemberDetails;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

  private final BoardFacade boardFacade;

  @PostMapping
  public BaseResponse<Void> createBoard(
      @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails,
      @RequestBody CreateBoardRequestDto requestDto) {
    boardFacade.createBoard(requestDto, Long.valueOf(memberDetails.getUsername()));
    return BaseResponse.onSuccess(null);
  }

  @PatchMapping("/{boardId}")
  public BaseResponse<Void> updateBoard(
      @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails,
      @PathVariable(name = "boardId") Long boardId,
      @RequestBody UpdateBoardRequestDto requestDto) {
    boardFacade.updateBoard(requestDto, boardId, Long.valueOf(memberDetails.getUsername()));
    return BaseResponse.onSuccess(null);
  }

  @DeleteMapping("/{boardId}")
  public BaseResponse<Void> deleteBoard(
      @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails,
      @PathVariable(name = "boardId") Long boardId) {
    boardFacade.deleteBoard(boardId, Long.valueOf(memberDetails.getUsername()));
    return BaseResponse.onSuccess(null);
  }

  @GetMapping("/{boardId}")
  public BaseResponse<GetBoardResponseDto> getBoard(
      @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails,
      @PathVariable(name = "boardId") Long boardId) {
    return BaseResponse.onSuccess(boardFacade.getBoard(boardId));
  }
}
