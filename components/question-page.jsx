import React from "react";
import {
    Divider,
    FlatButton,
    RadioButtonGroup,
    RadioButton,
    Stepper,
    Step,
    StepLabel,
    Toolbar,
    ToolbarGroup,
} from "material-ui";
import NavigationExpandMoreIcon from "material-ui/svg-icons/navigation/expand-more";
import ArrowBack from "material-ui/svg-icons/navigation/arrow-back";
import ArrowForward from "material-ui/svg-icons/navigation/arrow-forward";

class QuestionPage extends React.Component {
    render() {
        return <div style={{ "textAlign": "center" }}>
            <Stepper activeStep={2}>
                <Step><StepLabel></StepLabel></Step>
                <Step><StepLabel></StepLabel></Step>
                <Step><StepLabel></StepLabel></Step>
                <Step><StepLabel></StepLabel></Step>
                <Step><StepLabel></StepLabel></Step>
                <Step><StepLabel></StepLabel></Step>
                <Step><StepLabel></StepLabel></Step>
                <Step><StepLabel></StepLabel></Step>
                <Step><StepLabel></StepLabel></Step>
                <Step><StepLabel></StepLabel></Step>
            </Stepper>
            <Toolbar>
                <ToolbarGroup firstChild>
                    <FlatButton
                        onClick={() => { }}
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
                        onClick={() => { }}
                        target="_blank"
                        labelPosition="before"
                        label="Next"
                        icon={<ArrowForward />}
                    />
                </ToolbarGroup>
            </Toolbar>
            <h1>What does the fox say?</h1>
            <Divider />
        </div>;
    }
}

export default QuestionPage;