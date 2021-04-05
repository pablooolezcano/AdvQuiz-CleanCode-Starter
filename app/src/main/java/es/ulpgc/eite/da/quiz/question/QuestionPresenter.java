package es.ulpgc.eite.da.quiz.question;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.quiz.app.AppMediator;
import es.ulpgc.eite.da.quiz.app.CheatToQuestionState;
import es.ulpgc.eite.da.quiz.app.QuestionToCheatState;

public class QuestionPresenter implements QuestionContract.Presenter {

  public static String TAG = QuestionPresenter.class.getSimpleName();

  private AppMediator mediator;
  private WeakReference<QuestionContract.View> view;
  private QuestionState state;
  private QuestionContract.Model model;

  public QuestionPresenter(AppMediator mediator) {
    this.mediator = mediator;
    state = mediator.getQuestionState();
  }

  @Override
  public void onStart() {
    Log.e(TAG, "onStart()");

    // call the model
    state.question = model.getQuestion();
    state.option1 = model.getOption1();
    state.option2 = model.getOption2();
    state.option3 = model.getOption3();

    // reset state to tests
    state.answerCheated=false;
    state.optionClicked = false;
    state.option = 0;

    // update the view
    disableNextButton();
    view.get().resetReply();
  }


  @Override
  public void onRestart() {
    Log.e(TAG, "onRestart()");

    //TODO: falta implementacion
    if(state.optionClicked){
      if(model.isCorrectOption(state.option)){
        view.get().updateReply(true);
      } else{
        view.get().updateReply(false);
      }
      //onOptionButtonClicked(state.option);
    } else{
      view.get().resetReply();
    }

  }


  @Override
  public void onResume() {
    Log.e(TAG, "onResume()");

    //TODO: falta implementacion

    // use passed state if is necessary
    CheatToQuestionState savedState = getStateFromCheatScreen();
    if (savedState != null) {

      if(savedState.answerCheated){
        if(!model.hasQuizFinished()){
          onNextButtonClicked();
        } else{
          state.nextEnabled = false;
          state.optionEnabled = false;
        }

      }
      // fetch the model
    }

    // update the view
    view.get().displayQuestion(state);
  }


  @Override
  public void onDestroy() {
    Log.e(TAG, "onDestroy()");
  }

  @Override
  public void onOptionButtonClicked(int option) {
    Log.e(TAG, "onOptionButtonClicked()");

    //TODO: falta implementacion

    if(option == 1){
      state.option = 1;
    }
    if(option == 2){
      state.option = 2;
    }
    else{
      state.option = 3;
    }
    state.optionClicked = true;
    boolean isCorrect = model.isCorrectOption(option);

    if(isCorrect) {
        view.get().updateReply(isCorrect);
        state.cheatEnabled = false;
     } else {
        view.get().updateReply(isCorrect);
        state.cheatEnabled = true;
      }
    enableNextButton();
    //state.optionEnabled = false;

    view.get().displayQuestion(state);
//

  }

  @Override
  public void onNextButtonClicked() {
    Log.e(TAG, "onNextButtonClicked()");

    //TODO: falta implementacion
    state.quizIndex = state.quizIndex + 5;
    model.updateQuizIndex();

    state.question = model.getQuestion();
    state.option1 = model.getOption1();
    state.option2 = model.getOption2();
    state.option3 = model.getOption3();
    state.optionClicked = false;
    disableNextButton();
    view.get().displayQuestion(state);
    view.get().resetReply();
  }

  @Override
  public void onCheatButtonClicked() {
    Log.e(TAG, "onCheatButtonClicked()");
    String answer = model.getAnswer();
    QuestionToCheatState newState = new QuestionToCheatState(answer);
    passStateToCheatScreen(newState);
    Log.e(TAG, newState.answer);
    navigateToCheatScreen();
  }

  private void passStateToCheatScreen(QuestionToCheatState state) {

    //TODO: falta implementacion
    mediator.setQuestionToCheatState(state);
    Log.e(TAG, state + "");
  }

  private CheatToQuestionState getStateFromCheatScreen() {

    //TODO: falta implementacion

    return mediator.getCheatToQuestionState();
  }

  private void disableNextButton() {
    state.optionEnabled=true;
    state.cheatEnabled=true;
    state.nextEnabled=false;

  }

  private void enableNextButton() {
    state.optionEnabled=false;

    if(!model.hasQuizFinished()) {
      state.nextEnabled=true;
    }
  }
  private void navigateToCheatScreen(){
    view.get().navigateToCheatScreen();
  }

  @Override
  public void injectView(WeakReference<QuestionContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(QuestionContract.Model model) {
    this.model = model;
  }

}
