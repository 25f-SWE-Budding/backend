package kr.ac.korea.budding.repository;

import kr.ac.korea.budding.dto.UserItemAllResponseDto;
import kr.ac.korea.budding.entity.ItemEntity;
import kr.ac.korea.budding.entity.UserEntity;
import kr.ac.korea.budding.entity.UserItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserItemRepository extends JpaRepository<UserItemEntity, Long> {
     List<UserItemEntity> findAllByUserId(Long userId);

     Boolean existsByUserAndItem(UserEntity user, ItemEntity item);
}
