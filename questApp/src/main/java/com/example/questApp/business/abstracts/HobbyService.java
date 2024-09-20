package com.example.questApp.business.abstracts;

import java.util.List;

import com.example.questApp.business.requests.HobbyCreateRequest;
import com.example.questApp.business.responses.HobbyResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.Result;

public interface HobbyService {
	 DataResult<List<HobbyResponse>> getByUserId(Long userId);
	 DataResult<List<HobbyResponse>> add(List<HobbyCreateRequest> hobbyCreateRequests); 
	 Result delete(Long hobbyId);
}
