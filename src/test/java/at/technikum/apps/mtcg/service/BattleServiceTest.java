package at.technikum.apps.mtcg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.LobbyRepository;
import at.technikum.apps.mtcg.repository.UserRepository;
import at.technikum.apps.mtcg.repository.historyRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BattleServiceTest {

  private UserRepository userRepository;
  private CardRepository cardRepository;
  private LobbyRepository lobbyRepository;
  private historyRepository historyRepository;
  private BattleService battleService;

  @BeforeEach
  void setUp() {
    userRepository = mock(UserRepository.class);
    cardRepository = mock(CardRepository.class);
    lobbyRepository = mock(LobbyRepository.class);
    historyRepository = mock(historyRepository.class);
    battleService =
      new BattleService(
        userRepository,
        cardRepository,
        lobbyRepository,
        historyRepository
      );
  }

  @Test
  public void startBattle_NullToken_ThrowsException() {
    assertThrows(
      RuntimeException.class,
      () -> {
        battleService.startBattle(null);
      }
    );
  }

  @Test
  public void startBattle_DeckNotFull_ThrowsException() {
    when(userRepository.find(anyString())).thenReturn(Optional.of(new User()));
    when(cardRepository.getCardsFromUserWhereInDeckTrue(anyString()))
      .thenReturn(new ArrayList<>());

    assertThrows(
      RuntimeException.class,
      () -> {
        battleService.startBattle("token");
      }
    );
  }

  @Test
  public void startBattle_Success() {
    // Arrange
    User user1 = new User();
    user1.setUsername("user1");
    when(userRepository.find(null)).thenReturn(Optional.of(user1));
    when(cardRepository.getCardsFromUserWhereInDeckTrue(anyString()))
      .thenReturn(
        Arrays.asList(new Card(), new Card(), new Card(), new Card())
      );
    when(lobbyRepository.findBattle("user1")).thenReturn(user1);
    when(lobbyRepository.isLobbyEmpty(anyString())).thenReturn(false);

    BattleService spyBattleService = Mockito.spy(battleService);
    doAnswer(invocation -> null)
      .when(spyBattleService)
      .performBattle(any(), any(), any(), any(), any());

    // Act
    spyBattleService.startBattle("token");

    // Assert
    verify(spyBattleService).performBattle(any(), any(), any(), any(), any());
  }
}
