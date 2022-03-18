public class TwoStage {
    LinearProgramming lp;

    public TwoStage(LinearProgramming _lp) {
        lp = _lp;
    }

    public LinearProgramming getStageOneLP() {
        return lp;
    }
}
