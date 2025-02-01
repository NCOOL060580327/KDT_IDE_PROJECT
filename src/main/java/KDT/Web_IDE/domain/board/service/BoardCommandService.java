package KDT.Web_IDE.domain.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import KDT.Web_IDE.domain.board.dto.request.CreateBoardRequestDto;
import KDT.Web_IDE.domain.board.dto.request.UpdateBoardRequestDto;
import KDT.Web_IDE.domain.board.entity.Board;
import KDT.Web_IDE.domain.board.entity.repositroy.BoardRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardCommandService {

  private final BoardRepository boardRepository;

  public Board createBoard(CreateBoardRequestDto requestDto) {
    return boardRepository.save(Board.builder().title(requestDto.title()).build());
  }

  public void updateBoard(UpdateBoardRequestDto requestDto, Board board) {
    board.setTitle(requestDto.title());
  }

  public void deleteBoard(Board board) {
    boardRepository.delete(board);
  }
}
