package kr.ac.korea.budding.repository;

import kr.ac.korea.budding.entity.ItemEntity;
import kr.ac.korea.budding.enums.ItemSlots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    List<ItemEntity> findBySlot(ItemSlots slot);
}
