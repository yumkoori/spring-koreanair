package org.doit.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatPriceLoadDTO {
	    
    private String classid;  // class_id -> classid (명시적 매핑)
    private int price;
    
    @Override
    public String toString() {
        return "SeatPriceLoadDTO{" +
                "classid='" + classid + '\'' +
                ", price=" + price +
                '}';
    }
}
