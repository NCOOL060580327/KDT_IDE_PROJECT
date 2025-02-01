package KDT.Web_IDE.domain.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import KDT.Web_IDE.domain.board.entity.BoardUser;
import KDT.Web_IDE.domain.board.entity.repositroy.BoardUserRepository;
import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.custom.BoardException;
import KDT.Web_IDE.global.exception.custom.MemberException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardUserQueryService {

  private final BoardUserRepository boardUserRepository;

  public void isLeader(Long memberId, Long boardId) {
    BoardUser boardUser =
        boardUserRepository
            .findByMember_IdAndBoard_Id(memberId, boardId)
            .orElseThrow(() -> new MemberException(GlobalErrorCode.NOT_IN_BOARD));

    if (!boardUser.getIsLeader()) {
      throw new BoardException(GlobalErrorCode.NOT_LEADER);
    }
  }
}
