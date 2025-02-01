package KDT.Web_IDE.domain.board.dto.response;

import KDT.Web_IDE.domain.board.entity.Board;
import lombok.Builder;

@Builder
public record GetBoardResponseDto(Long boardId, String title, Integer userCount) {

  public static GetBoardResponseDto fromEntity(Board board) {
    return GetBoardResponseDto.builder()
        .boardId(board.getId())
        .title(board.getTitle())
        .userCount(board.getUserCount())
        .build();
  }
}
