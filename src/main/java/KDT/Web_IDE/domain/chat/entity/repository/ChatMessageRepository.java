package KDT.Web_IDE.domain.chat.entity.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import KDT.Web_IDE.domain.chat.entity.ChatMessage;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

  List<ChatMessage> findByChatRoomIdAndSenderIdOrderBySendTimeDesc(
      String chatRoomId, String memberId);
}
