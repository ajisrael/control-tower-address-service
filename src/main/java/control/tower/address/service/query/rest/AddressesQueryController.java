package control.tower.address.service.query.rest;

import control.tower.address.service.query.queries.FindAddressQuery;
import control.tower.core.query.queries.FindAllAddressesForUserQuery;
import control.tower.address.service.query.queries.FindAllAddressesQuery;
import control.tower.core.query.querymodels.AddressQueryModel;
import control.tower.core.rest.PageResponseType;
import control.tower.core.rest.PaginationResponse;
import control.tower.core.utils.PaginationUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

import static control.tower.core.constants.DomainConstants.DEFAULT_PAGE;
import static control.tower.core.constants.DomainConstants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("/addresses")
@Tag(name = "Address Query API")
public class AddressesQueryController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping()
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all addresses")
    public CompletableFuture<PaginationResponse<AddressQueryModel>> getAddresses(
            @RequestParam(defaultValue = DEFAULT_PAGE) int currentPage,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        FindAllAddressesQuery findAllAddressesQuery = FindAllAddressesQuery.builder()
                .pageable(PaginationUtility.buildPageable(currentPage, pageSize))
                .build();

        return queryGateway.query(findAllAddressesQuery, new PageResponseType<>(AddressQueryModel.class))
                .thenApply(PaginationUtility::toPageResponse);
    }

    @GetMapping(params = "addressId", path = "/id")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get address by id")
    public CompletableFuture<AddressQueryModel> getAddress(String addressId) {
        return queryGateway.query(new FindAddressQuery(addressId),
                ResponseTypes.instanceOf(AddressQueryModel.class));
    }

    @GetMapping(params = "userId", path = "/user")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all addresses for user by user id")
    public CompletableFuture<PaginationResponse<AddressQueryModel>> getAddressesForUser(
            String userId,
            @RequestParam(defaultValue = DEFAULT_PAGE) int currentPage,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        FindAllAddressesForUserQuery findAllAddressesForUserQuery = FindAllAddressesForUserQuery.builder()
                .userId(userId)
                .pageable(PaginationUtility.buildPageable(currentPage, pageSize))
                .build();

        return queryGateway.query(findAllAddressesForUserQuery, new PageResponseType<>(AddressQueryModel.class))
                .thenApply(PaginationUtility::toPageResponse);
    }
}
