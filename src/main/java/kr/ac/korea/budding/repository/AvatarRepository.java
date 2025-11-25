package kr.ac.korea.budding.repository;

import kr.ac.korea.budding.entity.AvatarEntity;
import kr.ac.korea.budding.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<AvatarEntity,Integer> {
//    Optional<AvatarEntity> findByUser(UserEntity user);
}
