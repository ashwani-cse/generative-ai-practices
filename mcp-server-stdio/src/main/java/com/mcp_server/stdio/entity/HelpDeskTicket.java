package com.mcp_server.stdio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Ashwani Kumar
 * Created on 27/01/26.
 */
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "help_desk_tickets")
public class HelpDeskTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String issue;
    private String status; // e.g. "OPEN", "IN_PROGRESS", "CLOSED"
    private LocalDateTime createdAt;
    private LocalDateTime eta;
    private String username;

}