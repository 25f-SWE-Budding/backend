package kr.ac.korea.budding.service;

import jakarta.transaction.Transactional;
import kr.ac.korea.budding.dto.ItemRequestDto;
import kr.ac.korea.budding.dto.ItemResponseDto;
import kr.ac.korea.budding.entity.ItemEntity;
import kr.ac.korea.budding.enums.ItemSlots;
import kr.ac.korea.budding.mapper.ItemMapper;
import kr.ac.korea.budding.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    // 아이템 생성
    @Transactional
    public ItemResponseDto createItem(ItemRequestDto req) {
        ItemEntity item = ItemEntity.builder()
                .slot(req.getSlot())
                .name(req.getName())
                .imageUrl(req.getImageUrl())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        itemRepository.save(item);

        return itemMapper.toDto(item);
    }

    // 아이템 변경
    @Transactional
    public ItemResponseDto updateItem(ItemRequestDto req, Long itemId) {
        ItemEntity item = itemRepository.findById(itemId).orElseThrow(
                () -> new RuntimeException(String.format("Item with id %d not found", itemId))
        );

        item.setSlot(req.getSlot());
        item.setName(req.getName());
        item.setImageUrl(req.getImageUrl());
        item.setUpdatedAt(LocalDateTime.now());

        itemRepository.save(item);

        return itemMapper.toDto(item);
    }

    // 아이템 부위별 검색
    @Transactional
    public List<ItemResponseDto> findItemsBySlot(ItemSlots slot) {
        List<ItemEntity> items = itemRepository.findBySlot(slot);

        return itemMapper.toDto(items);
    }
}
