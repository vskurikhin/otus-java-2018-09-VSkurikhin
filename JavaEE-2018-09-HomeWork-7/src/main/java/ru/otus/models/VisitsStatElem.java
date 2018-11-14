package ru.otus.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitsStatElem {
    private long id;
    private long prev_id;
    private String sessionID;
    private String statMarkerName;
    private String pageName;
    private String clientIPAddress;
    private String browserName;
    private LocalDateTime clientTime;
    private LocalDateTime serverTime;
}
