package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.EventHistory;
import at.technikum.apps.mtcg.repository.historyRepository;

public class HistoryService {

  private historyRepository historyRepository = new historyRepository();

  public EventHistory[] getHistory(String token) {
    return historyRepository.getHistory(token);
  }
}
