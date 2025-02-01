package KDT.Web_IDE.domain.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import KDT.Web_IDE.domain.board.entity.Board;
import KDT.Web_IDE.domain.board.entity.repositroy.BoardRepository;
import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.custom.BoardException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardQueryService {

  private final BoardRepository boardRepository;

  public Board getBoardByBoardId(Long boardId) {
    return boardRepository
        .findById(boardId)
        .orElseThrow(() -> new BoardException(GlobalErrorCode.BOARD_NOT_FOUND));
  }
}
