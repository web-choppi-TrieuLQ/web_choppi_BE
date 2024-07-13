package tlq.com.tlq_sale_management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import tlq.com.tlq_sale_management.dto.request.UserRequest.UserCreationRequest;
import tlq.com.tlq_sale_management.dto.request.UserRequest.UserUpdateRequest;
import tlq.com.tlq_sale_management.dto.response.UserResponse;
import tlq.com.tlq_sale_management.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);

}
