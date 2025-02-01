package KDT.Web_IDE.domain.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import KDT.Web_IDE.domain.board.entity.Board;
import KDT.Web_IDE.domain.board.entity.BoardUser;
import KDT.Web_IDE.domain.board.entity.repositroy.BoardUserRepository;
import KDT.Web_IDE.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardUserCommandService {

  private final BoardUserRepository boardUserRepository;

  public void createBoardUser(Board board, Member member, Boolean isLeader) {
    boardUserRepository.save(
        BoardUser.builder().board(board).member(member).isLeader(isLeader).build());
  }
}
