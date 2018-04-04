import React from "react";
import { connect } from "react-redux";
import TextField from "material-ui/TextField";
import { selectAnswer } from "../store/actions/questions";

class OpenAnswer extends React.Component {
    render() {
        return <TextField
            hintText="Your answer"
            value={this.props.questions.questions[this.props.questions.currentQuestionIndex].answer}
            onChange={(event, newValue) => this.props.selectAnswer(newValue)}
        />;
    }
}

export default connect(store => {
    return {
        questions: store.questions
    };
}, { selectAnswer })(OpenAnswer);