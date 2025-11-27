package kr.ac.korea.budding.service;

import jakarta.transaction.Transactional;
import kr.ac.korea.budding.dto.AvatarRequestDto;
import kr.ac.korea.budding.dto.AvatarResponseDto;
import kr.ac.korea.budding.entity.AvatarEntity;
import kr.ac.korea.budding.entity.ItemEntity;
import kr.ac.korea.budding.entity.UserEntity;
import kr.ac.korea.budding.enums.ItemSlots;
import kr.ac.korea.budding.mapper.AvatarMapper;
import kr.ac.korea.budding.repository.AvatarRepository;
import kr.ac.korea.budding.repository.ItemRepository;
import kr.ac.korea.budding.repository.UserItemRepository;
import kr.ac.korea.budding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final UserRepository userRepository;
    private final UserItemRepository userItemRepository;
    private final ItemRepository itemRepository;

    private final AvatarMapper avatarMapper;

    // 아바타 설정
    @Transactional
    public AvatarResponseDto setAvatar(Long userId, AvatarRequestDto req) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(String.format("user with id: %d not found", userId)));

        // 구매한 아이템인지 확인
        ItemEntity hat = validateOwnedItemOrNull(user, req.getHat(), ItemSlots.HAT);
        ItemEntity eyewear = validateOwnedItemOrNull(user, req.getEyewear(), ItemSlots.EYEWEAR);
        ItemEntity top     = validateOwnedItemOrNull(user, req.getTop(),     ItemSlots.TOP);
        ItemEntity bottom  = validateOwnedItemOrNull(user, req.getBottom(),  ItemSlots.BOTTOM);
        ItemEntity shoes   = validateOwnedItemOrNull(user, req.getShoes(),   ItemSlots.SHOES);

        AvatarEntity avatar = user.getAvatar();

        avatar.setHat(hat);
        avatar.setEyewear(eyewear);
        avatar.setTop(top);
        avatar.setBottom(bottom);
        avatar.setShoes(shoes);

        avatarRepository.save(avatar);

        return avatarMapper.toDto(avatar);
    }

    // userId로 avatar 얻기
    @Transactional
    public AvatarResponseDto getAvatarByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(String.format("user with id: %d not found", userId)));

        AvatarEntity avatar = user.getAvatar();

        return avatarMapper.toDto(avatar);
    }


    // 유저가 아이템을 가지고 있는지 확인하는 보조 함수
    private ItemEntity validateOwnedItemOrNull(
            UserEntity user,
            String itemName,
            ItemSlots expectedSlot
    ) {
        // null이면 장착 해제 용도로 사용 (검사하지 않고 null 리턴)
        if (itemName == null) {
            return null;
        }

        // 1) 아이템 존재 여부 확인
        ItemEntity item = itemRepository.findByName(itemName)
                .orElseThrow(() -> new RuntimeException(
                        String.format("item with name: %s not found", itemName)
                ));

        // 2) 슬롯 타입 맞는지 확인 (모자 슬롯에 신발 끼우는 것 방지)
        if (item.getSlot() != expectedSlot) {
            throw new RuntimeException(
                    String.format("item id %s is not %s slot", itemName, expectedSlot)
            );
        }

        // 3) 이 유저가 이 아이템을 실제로 가지고 있는지 확인
        boolean owns = userItemRepository.existsByUserAndItem(user, item);
        if (!owns) {
            throw new RuntimeException(
                    String.format("user %d does not own item %s", user.getId(), itemName)
            );
        }

        return item;
    }
}
