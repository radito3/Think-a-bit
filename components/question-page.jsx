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
    addQuestions,
    nextQuestion,
    previousQuestion
} from "../store/actions/questions";
import ClosedOneAnswer from "./closed-one-answer.jsx";
import ClosedManyAnswer from "./closed-many-answer.jsx";
import OpenAnswer from "./open-answer.jsx";

class QuestionPage extends React.Component {
    constructor(props) {
        super(props);

        this.props.addQuestions([
            {
                title: "What does the fox say?",
                type: "CLOSED_MANY",
                answers: [
                    {
                        content: "Meaw",
                        isSelected: false
                    },
                    {
                        content: "Woof",
                        isSelected: false
                    },
                    {
                        content: "Hiss",
                        isSelected: false
                    },
                    {
                        content: "Wreack",
                        isSelected: false
                    },
                    {
                        content: "Cough",
                        isSelected: false
                    }
                ]
            },
            {
                title: "When did WW1 begin?",
                type: "CLOSED_ONE",
                answers: [
                    {
                        content: "1912",
                        isSelected: false
                    },
                    {
                        content: "1913",
                        isSelected: false
                    },
                    {
                        content: "1914",
                        isSelected: false
                    },
                    {
                        content: "1915",
                        isSelected: false
                    }
                ]
            },
            {
                title: "When was Queen Elizabeth II born?",
                type: "OPEN",
                answer: ""
            },
            {
                title: "Can Chuck Norris break this site?"
            }
        ]);
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
                        onClick={() => { this.props.previousQuestion(); }}
                        target="_blank"
                        labelPosition="after"
                        label="Previous"
                        icon={<ArrowBack />}
                    />
                </ToolbarGroup>
                <ToolbarGroup>
                    <FlatButton label="Finish" primary={true} />
                </ToolbarGroup>
                <ToolbarGroup lastChild>
                    <FlatButton
                        onClick={() => { this.props.nextQuestion() }}
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
        questions: store.questions
    };
}, {
    addQuestions,
    nextQuestion,
    previousQuestion
 })(QuestionPage);