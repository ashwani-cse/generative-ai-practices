package com.mcp_server.stdio.repository;

import com.mcp_server.stdio.entity.HelpDeskTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ashwani Kumar
 * Created on 27/01/26.
 */
public interface HelpDeskTicketRepository extends JpaRepository<HelpDeskTicket, Long> {

    List<HelpDeskTicket> findByUsername(String username);
}
