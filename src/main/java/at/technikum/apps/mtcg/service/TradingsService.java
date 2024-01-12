package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.auth.Auth;
import at.technikum.apps.mtcg.entity.Trading;
import at.technikum.apps.mtcg.helper.Helper;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.TradingRepository;
import java.util.List;

public class TradingsService {

  private final Auth auth = new Auth();
  private final TradingRepository tradingRepository;
  private final CardRepository cardRepository = new CardRepository();

  public TradingsService() {
    this.tradingRepository = new TradingRepository();
  }

  public List<Trading> findAll() {
    return tradingRepository.findAll();
  }

  public Trading save(Trading trading, String token) {
    if (token == null) {
      throw new RuntimeException("UnauthorizedError");
    }
    if (
      !auth
        .extractUsernameFromToken(token)
        .equals(trading.getCard().getOwnerUsername())
    ) {
      throw new RuntimeException("Card does not belong to you");
    }
    if (trading.getCard().isInDeck()) {
      throw new RuntimeException("Card is in deck. Not allowed to trade it");
    }
    if (tradingRepository.isTradingIdPosted(trading.getId())) {
      throw new RuntimeException("Trading already exists");
    }
    return tradingRepository.save(trading);
  }

  public Boolean delete(String id, String token) {
    if (token == null) {
      throw new RuntimeException("UnauthorizedError");
    }
    if (!tradingRepository.isTradingIdPosted(id)) {
      throw new RuntimeException("Trading does not exist");
    }
    if (
      !auth
        .extractUsernameFromToken(token)
        .equals(tradingRepository.findById(id).getCard().getOwnerUsername())
    ) {
      throw new RuntimeException("Trading does not belong to you");
    }

    return tradingRepository.deleteById(id);
  }

  public void acceptTrade(String tradeId, String token, String cardId) {
    if (token == null) {
      throw new RuntimeException("UnauthorizedError");
    }
    if (!tradingRepository.isTradingIdPosted(tradeId)) {
      throw new RuntimeException("Trading does not exist");
    }
    if (
      auth
        .extractUsernameFromToken(token)
        .equals(
          tradingRepository.findById(tradeId).getCard().getOwnerUsername()
        )
    ) {
      throw new RuntimeException("You cannot trade with yourself");
    }
    if (cardRepository.getCardById(cardId).isInDeck()) {
      throw new RuntimeException("Card is in deck. Not allowed to trade it");
    }
    if (
      !cardRepository
        .getCardById(cardId)
        .getOwnerUsername()
        .equals(auth.extractUsernameFromToken(token))
    ) {
      throw new RuntimeException("Card does not belong to you");
    }
    if (
      tradingRepository.findById(tradeId).getMinimumDamage() >
      cardRepository.getCardById(cardId).getDamage() ||
      !Helper
        .getTypeFromCard(
          tradingRepository.findById(tradeId).getCard().getName()
        )
        .equals(
          Helper.getTypeFromCard(cardRepository.getCardById(cardId).getName())
        )
    ) {
      throw new RuntimeException("Card does not meet minimum requirements");
    }

    if (!tradingRepository.executeTrade(tradeId, cardId)) {
      throw new RuntimeException("Something went wrong");
    }
  }
}
