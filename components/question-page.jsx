import React from "react";
import { connect } from "react-redux";
import {
    Divider,
    FlatButton,
    Stepper,
    Step,
    StepLabel,
    Toolbar,
    ToolbarGroup,
} from "material-ui";
import NavigationExpandMoreIcon from "material-ui/svg-icons/navigation/expand-more";
import ArrowBack from "material-ui/svg-icons/navigation/arrow-back";
import ArrowForward from "material-ui/svg-icons/navigation/arrow-forward";
import {
    nextQuestion,
    previousQuestion
} from "../store/actions/questions";
import ClosedOneAnswer from "./closed-one-answer.jsx";
import ClosedManyAnswer from "./closed-many-answer.jsx";
import OpenAnswer from "./open-answer.jsx";
import config from "../config";

class QuestionPage extends React.Component {
    constructor(props) {
        super(props);
    }

    submit() {
        const submitBody = {
            categoryId: this.props.stages.selectedCategoryId,
            stageId: this.props.questions.currentStageId,
            results: this.props.questions.questions.map(question => {
                return {
                    questionId: question.id,
                    questionType: question.type,
                    questionTitle: question.title,
                    answers: question.answers.filter(answer => answer.isSelected).map(answer => answer.content)
                };
            })
        }

        fetch(`${config.url}:${config.port}/Think-a-bit/game/submit`, {
            method: "POST",
            headers: { "Content-Type": "application/json", },
            credentials: "same-origin",
            body: JSON.stringify(submitBody)
        }).then(response => {
            if (response.status === 401) {
                console.log("Session expired");
            } else if (response.status === 403) {
                console.log("Stage unavailable");
            } else if (response.status === 200) {
                return response.json();
            } else {
                console.log("Unknown error");
            }
        }).then(parsed => {
            console.log(parsed);
        }).catch(error => {
            console.log(error);
        });
    }

    render() {
        let answer;
        switch (this.props.questions.questions[this.props.questions.currentQuestionIndex].type) {
            case "CLOSED_ONE":
                answer = <ClosedOneAnswer/>
                break;
            case "CLOSED_MANY":
                answer = <ClosedManyAnswer/>
                break;
            case "OPEN":
                answer = <OpenAnswer/>
                break;
            default:
                answer = <p>Sorry, an error has occurred.</p>;
                break;
        }

        return <div style={{ "textAlign": "center" }}>
            <Stepper activeStep={this.props.questions.currentQuestionIndex}>
                {this.props.questions.questions.map(question =>
                    <Step key={question.title}><StepLabel></StepLabel></Step>
                )}
            </Stepper>
            <Toolbar>
                <ToolbarGroup firstChild>
                    <FlatButton
                        onClick={() => this.props.previousQuestion() }
                        target="_blank"
                        labelPosition="after"
                        label="Previous"
                        icon={<ArrowBack />}
                    />
                </ToolbarGroup>
                <ToolbarGroup>
                    <FlatButton
                        label="Finish"
                        primary={true}
                        onClick={() => this.submit() }
                    />
                </ToolbarGroup>
                <ToolbarGroup lastChild>
                    <FlatButton
                        onClick={() => this.props.nextQuestion() }
                        target="_blank"
                        labelPosition="before"
                        label="Next"
                        icon={<ArrowForward />}
                    />
                </ToolbarGroup>
            </Toolbar>
            <h1>{this.props.questions.questions[this.props.questions.currentQuestionIndex].title}</h1>
            <Divider />
            {answer}
        </div>;
    }
}

export default connect(store => {
    return {
        questions: store.questions,
        stages: store.stages
    };
}, {
    nextQuestion,
    previousQuestion
 })(QuestionPage);