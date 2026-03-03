package exchangetask27.service;

import exchangetask27.dto.ConvertRequestDto;
import exchangetask27.dto.ConvertResponseDto;

public interface CurrencyService {
    ConvertResponseDto convert(ConvertRequestDto request);
}
