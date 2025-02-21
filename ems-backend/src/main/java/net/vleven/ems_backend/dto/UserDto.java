package net.vleven.ems_backend.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.vleven.ems_backend.model.Role;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    
	private Long id;
    private String username;
    private List<String> roles;
    
    public UserDto(Long id, String username, Set<Role> set) {
        this.id = id;
        this.username = username;
        this.roles = set.stream()
                        .map(Enum::name)  // Convert Role enum to String
                        .collect(Collectors.toList());  
    }


	
    
   
    
}
