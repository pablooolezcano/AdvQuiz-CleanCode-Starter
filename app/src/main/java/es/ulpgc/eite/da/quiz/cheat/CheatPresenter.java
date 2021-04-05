package es.ulpgc.eite.da.quiz.cheat;

import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.quiz.R;
import es.ulpgc.eite.da.quiz.app.AppMediator;
import es.ulpgc.eite.da.quiz.app.CheatToQuestionState;
import es.ulpgc.eite.da.quiz.app.QuestionToCheatState;

public class CheatPresenter implements CheatContract.Presenter {

  public static String TAG = CheatPresenter.class.getSimpleName();

  private AppMediator mediator;
  private WeakReference<CheatContract.View> view;
  private CheatState state;
  private CheatContract.Model model;

  public CheatPresenter(CheatState state) {
    this.state = state;
  }

  public CheatPresenter(AppMediator mediator) {
    this.mediator = mediator;
    state = mediator.getCheatState();
  }


  @Override
  public void onStart() {
    Log.e(TAG, "onStart()");

    // reset state to tests
    state.answerEnabled=true;
    state.answerCheated=false;
    state.answer = null;

    // update the view
    view.get().resetAnswer();
  }

  @Override
  public void onRestart() {
    Log.e(TAG, "onRestart()");

    //TODO: falta implementacion
    if(state.answerCheated){
      state.yesButton = false;
      state.noButton = false;
      state.answer = "América del Sur";
    }
    view.get().displayAnswer(state);
  }

  @Override
  public void onResume() {
    Log.e(TAG, "onResume()");

    //TODO: falta implementacion

    // use passed state if is necessary
    QuestionToCheatState savedState = getStateFromQuestionScreen();
    if (savedState != null) {

      // fetch the model

      model.setAnswer(savedState.answer);
      state.answer = "???";
      //view.get().resetAnswer();

      // update the state

    }

    // update the view
    if(!state.answerCheated) {
      state.yesButton = true;
      state.noButton = true;
    }
    view.get().displayAnswer(state);

  }

  @Override
  public void onDestroy() {
    Log.e(TAG, "onDestroy()");
  }

  @Override
  public void onBackPressed() {
    Log.e(TAG, "onBackPressed()");

    //TODO: falta implementacion
  CheatToQuestionState newState = new CheatToQuestionState(state.answerCheated);
  passStateToQuestionScreen(newState);
  view.get().onFinish();
  }

  @Override
  public void onWarningButtonClicked(int option) {
    Log.e(TAG, "onWarningButtonClicked()");
    //TODO: falta implementacion
    //option=1 => yes, option=0 => no

    //Log.e(TAG, savedState.answer);
      if (option == 1) {
        state.answerCheated = true;
        state.yesButton = false;
        state.noButton = false;
        state.answer = model.getAnswer();
        //state.answerEnabled = false;
        view.get().displayAnswer(state);
      } else {
        //state.answerCheated = false;
        //CheatToQuestionState newState = new CheatToQuestionState(state.answerCheated);
        view.get().onFinish();
      }

  }

  private void passStateToQuestionScreen(CheatToQuestionState state) {


    mediator.setCheatToQuestionState(state);
  }

  private QuestionToCheatState getStateFromQuestionScreen() {



    return mediator.getQuestionToCheatState();
  }

  @Override
  public void injectView(WeakReference<CheatContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(CheatContract.Model model) {
    this.model = model;
  }

}
