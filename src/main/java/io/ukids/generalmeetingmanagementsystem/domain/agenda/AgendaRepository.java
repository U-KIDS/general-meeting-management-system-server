package io.ukids.generalmeetingmanagementsystem.domain.agenda;

import io.ukids.generalmeetingmanagementsystem.domain.meeting.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    @Override
    Optional<Agenda> findById(Long aLong);
    List<Agenda> findAllByMeetingId(Long id);
    void deleteAllByMeetingId(Long id);
}

