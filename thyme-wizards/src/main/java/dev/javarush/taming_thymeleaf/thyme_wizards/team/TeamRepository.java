package dev.javarush.taming_thymeleaf.thyme_wizards.team;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface TeamRepository
    extends CrudRepository<Team, TeamId>, PagingAndSortingRepository<Team, TeamId>,
    TeamRepositoryCustom {
  @Query("select new dev.javarush.taming_thymeleaf.thyme_wizards.team.TeamSummary(t.id, t.name, t.coach.id, t.coach.username) from Team t")
  Page<TeamSummary> finalAllSummary(Pageable pageable);
}
