import React from "react";
import { connect } from "react-redux";
import { GridList, RadioButton, RaisedButton } from "material-ui";
import { selectAnswer } from "../store/actions/questions";

class ClosedOneAnswer extends React.Component {
    render() {
        return <GridList
            cellHeight={180}
            // style={styles.gridList}
            cols={4}
            padding={8}
        >
            {this.props.questions.questions[this.props.questions.currentQuestionIndex].answers.map(answer => (
                <RaisedButton
                    key={answer.content}
                    buttonStyle={{
                        "fontSize": "20px",
                        "textAlign": "center",
                        "margin": "auto"
                    }}
                    style={{
                        "height": "100%",
                        "width": "100%"
                    }}
                ><RadioButton
                        label={answer.content}
                        checked={answer.isSelected}
                        onCheck={(event, isInputChecked) => { this.props.selectAnswer(event.target.parentElement.children[1].children[1].innerHTML) }}
                        style={{
                            "width": "100%",
                            "height": "100%"
                        }}
                    />
                </RaisedButton>
            ))}
        </GridList>;
    }
}

export default connect(store => {
    return {
        questions: store.questions
    };
}, { selectAnswer })(ClosedOneAnswer);