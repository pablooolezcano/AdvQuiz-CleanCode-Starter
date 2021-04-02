package es.ulpgc.eite.da.quiz.app;

import java.util.Objects;

public class CheatToQuestionState {

  public boolean answerCheated;

  public CheatToQuestionState(Boolean answerCheated){
    this.answerCheated = answerCheated;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    CheatToQuestionState that = (CheatToQuestionState) obj;
    return answerCheated == that.answerCheated;
  }

  @Override
  public int hashCode() {
    return Objects.hash(answerCheated);
  }
}
