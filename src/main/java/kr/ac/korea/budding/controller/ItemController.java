package kr.ac.korea.budding.controller;

import jakarta.websocket.server.PathParam;
import kr.ac.korea.budding.dto.ItemRequestDto;
import kr.ac.korea.budding.dto.ItemResponseDto;
import kr.ac.korea.budding.enums.ItemSlots;
import kr.ac.korea.budding.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("")
    public ItemResponseDto createItem(
            @RequestBody ItemRequestDto itemRequestDto
    ) {
        return itemService.createItem(itemRequestDto);
    }

    @PatchMapping("/{itemId}")
    public ItemResponseDto updateItem(
            @RequestBody ItemRequestDto itemRequestDto,
            @PathVariable Long itemId
    ) {
        return itemService.updateItem(itemRequestDto, itemId);
    }

    @GetMapping("")
    public List<ItemResponseDto> getItem(
        @RequestParam ItemSlots slot
    ) {
        return itemService.findItemsBySlot(slot);
    }
}
