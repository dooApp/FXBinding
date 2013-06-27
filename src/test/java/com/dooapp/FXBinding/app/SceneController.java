/*
 * Copyright (c) 2012, dooApp <contact@dooapp.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of dooApp nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.dooapp.FXBinding.app;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class SceneController {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="addAPointToSelectedSeriesButton"
    private Button addAPointToSelectedSeriesButton; // Value injected by FXMLLoader
    @FXML // fx:id="addASeriesButton"
    private Button addASeriesButton; // Value injected by FXMLLoader
    @FXML // fx:id="graph"
    private LineChart<Number, Number> graph; // Value injected by FXMLLoader
    @FXML // fx:id="listView"
    private ListView<XYChart.Series<Number, Number>> listView; // Value injected by FXMLLoader
    @FXML // fx:id="randomValuesButton"
    private Button randomValuesButton; // Value injected by FXMLLoader
    @FXML // fx:id="removeAPointFromSelectedSeries"
    private Button removeAPointFromSelectedSeries; // Value injected by FXMLLoader
    @FXML // fx:id="removeSelectedSeriesButton"
    private Button removeSelectedSeriesButton; // Value injected by FXMLLoader
    // Handler for Button[fx:id="addAPointToSelectedSeriesButton"] onAction
    @FXML
    void addAPointToSelectedSeries(ActionEvent event) {
        XYChart.Series<Number, Number> selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.getData().add(new XYChart.Data<Number, Number>(getARandomValue(), getARandomValue()));
        }
    }
    // Handler for Button[fx:id="addASerieButton"] onAction
    @FXML
    void addASeries(ActionEvent event) {
        XYChart.Series<Number, Number> s = new XYChart.Series<Number, Number>();
        do {
            s.getData().add(new XYChart.Data<Number, Number>(getARandomValue(), getARandomValue()));
        } while (Math.random() > 0.3);
        graph.getData().add(s);
    }
    // Handler for Button[fx:id="randomValuesButton"] onAction
    @FXML
    void randomValues(ActionEvent event) {
        XYChart.Series<Number, Number> selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            for (XYChart.Data<Number, Number> data : selected.getData()) {
                data.setXValue(getARandomValue());
                data.setYValue(getARandomValue());
            }
        } else {
            for (XYChart.Series<Number, Number> series : listView.getItems()) {
                for (XYChart.Data<Number, Number> data : series.getData()) {
                    data.setXValue(getARandomValue());
                    data.setYValue(getARandomValue());
                }
            }
        }
    }
    // Handler for Button[fx:id="removeAPointFromSelectedSerie"] onAction
    @FXML
    void removeAPointFromSelectedSeries(ActionEvent event) {
        XYChart.Series<Number, Number> selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null && !selected.getData().isEmpty()) {
            selected.getData().remove(selected.getData().get(0));
        } else {
            System.out.println("Nothing to remove");
        }
    }
    // Handler for Button[fx:id="removeSelectedSeriesButton"] onAction
    @FXML
    void removeSelectedSeries(ActionEvent event) {
        graph.getData().remove(listView.getSelectionModel().getSelectedItem());
    }
    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert addAPointToSelectedSeriesButton != null : "fx:id=\"addAPointToSelectedSerieButton\" was not injected: " + "check your FXML file 'Scene.fxml'.";
        assert addASeriesButton != null : "fx:id=\"addASerieButton\" was not injected: check your FXML file 'Scene" + ".fxml'.";
        assert graph != null : "fx:id=\"graph\" was not injected: check your FXML file 'Scene.fxml'.";
        assert listView != null : "fx:id=\"listView\" was not injected: check your FXML file 'Scene.fxml'.";
        assert randomValuesButton != null : "fx:id=\"randomValuesButton\" was not injected: check your FXML file 'Scene.fxml'.";
        assert removeAPointFromSelectedSeries != null : "fx:id=\"removeAPointFromSelectedSerie\" was not injected: " + "check your FXML file 'Scene.fxml'.";
        assert removeSelectedSeriesButton != null : "fx:id=\"removeSelectedSerieButton\" was not injected: check your" + " FXML file 'Scene.fxml'.";
        // Initialize your logic here: all @FXML variables will have been injected
        Bindings.bindContent(listView.getItems(), graph.getData());
        BooleanBinding selectionIsNull = listView.getSelectionModel().selectedItemProperty().isNull();
        addAPointToSelectedSeriesButton.disableProperty().bind(selectionIsNull);
        removeAPointFromSelectedSeries.disableProperty().bind(selectionIsNull);
        removeSelectedSeriesButton.disableProperty().bind(selectionIsNull);
    }
    private double getARandomValue() {
        return Math.random() * 200 - 100;
    }
}
