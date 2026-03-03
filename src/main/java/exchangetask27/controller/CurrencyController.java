package exchangetask27.controller;

import exchangetask27.dto.ConvertRequestDto;
import exchangetask27.dto.ConvertResponseDto;
import exchangetask27.service.CurrencyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping("/convert")
    public ConvertResponseDto convert(
            @RequestBody ConvertRequestDto request) {

        return currencyService.convert(request);
    }
}