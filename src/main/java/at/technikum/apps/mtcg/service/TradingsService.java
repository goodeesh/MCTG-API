package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Trading;
import at.technikum.apps.mtcg.repository.TradingRepository;
import java.util.List;

public class TradingsService {

  private final TradingRepository tradingRepository;

  public TradingsService() {
    this.tradingRepository = new TradingRepository();
  }

  public List<Trading> findAll() {
    return tradingRepository.findAll();
  }
}
