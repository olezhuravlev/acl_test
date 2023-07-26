package de.pim.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import de.pim.spring.model.Item;

@AllArgsConstructor
@Data
public class ItemDto {

    private long id;

    @NotBlank(message = "{field-must-be-filled}")
    @Size(min = 2, max = 256, message = "{expected-size-2-256}")
    private String title;

    public static ItemDto toDto(Item item) {
        return new ItemDto(item.getId(), item.getTitle());
    }
}
