package kr.ac.korea.budding.service;

import jakarta.transaction.Transactional;
import kr.ac.korea.budding.dto.ItemRequestDto;
import kr.ac.korea.budding.dto.ItemResponseDto;
import kr.ac.korea.budding.entity.ItemEntity;
import kr.ac.korea.budding.entity.UserEntity;
import kr.ac.korea.budding.entity.UserItemEntity;
import kr.ac.korea.budding.enums.ItemSlots;
import kr.ac.korea.budding.mapper.ItemMapper;
import kr.ac.korea.budding.repository.ItemRepository;
import kr.ac.korea.budding.repository.UserItemRepository;
import kr.ac.korea.budding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserRepository userRepository;
    private final UserItemRepository userItemRepository;

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

    // 아이템 구매
    @Transactional
    public ItemResponseDto buyItem(Long ItemId, Long userId) {
        ItemEntity item = itemRepository.findById(ItemId).orElseThrow(
                () -> new RuntimeException(String.format("Item with id %d not found", ItemId))
        );

        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException(String.format("User with id %d not found", userId))
        );

        if(user.getPoints() >= item.getPrice()) {
            user.setPoints(user.getPoints() - item.getPrice());
            userRepository.save(user);

            UserItemEntity userItem = new UserItemEntity();
            userItem.setUser(user);
            userItem.setItem(item);
            userItemRepository.save(userItem);

            return itemMapper.toDto(item);
        } else {
            throw new RuntimeException(
                    String.format("points insufficient. point: %d, price: %d", user.getPoints(), item.getPrice())
            );
        }
    };

    // 유저가 보유중인 아이템 확인
    public List<ItemResponseDto> getUserItems(Long userId) {

        List<UserItemEntity> userItems = userItemRepository.findAllByUserId(userId);

        List<ItemEntity> items = userItems.stream().map(UserItemEntity::getItem).toList();

        return itemMapper.toDto(items);
    }
}