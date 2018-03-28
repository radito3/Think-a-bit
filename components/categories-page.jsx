import React from "react";
import { Redirect, withRouter } from "react-router-dom";
import { connect } from "react-redux";
import { GridList, RaisedButton } from "material-ui";
import StarBorder from "material-ui/svg-icons/toggle/star-border";
import { addCategories, removeCategories } from "../store/actions/categories";

class CategoriesPage extends React.Component {
    constructor(props) {
        super(props);

        this.props.addCategories([
            "Biology",
            "Literature",
            "History",
            "Maths",
            "Astronomy",
            "Geography",
            "Programming",
            "All"
        ]);
    }

    render() {
        if (!this.props.authentication.username) {
            return <Redirect to="/login" />;
        }

        const styles = {
            root: {
                display: 'flex',
                flexWrap: 'wrap',
                justifyContent: 'space-around',
            },
            gridList: {
                width: '100%',
                height: '100%',
                overflowY: 'auto',
            }
        };

        return <div style={styles.root}>
            <GridList
                cellHeight={180}
                style={styles.gridList}
                cols={4}
                padding={8}
            >
                {this.props.categories.map(category => (
                    <RaisedButton
                    key={category}
                        buttonStyle={{
                            "fontSize": "20px",
                            "textAlign": "center",
                            "margin": "auto"
                        }}
                        style={{
                            "height": "100%",
                            "width": "100%"
                        }}
                    >{category}
                    </RaisedButton>
                ))}
            </GridList>
        </div>;
    }
}

export default withRouter(connect(store => {
    return {
        categories: store.categories.categories,
        authentication: store.authentication
    };
}, { addCategories, removeCategories })(CategoriesPage));