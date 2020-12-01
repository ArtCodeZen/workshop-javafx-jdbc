package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import gui.listeners.*;
import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.Seller;
import model.exception.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable {
	private SellerService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	private Seller entity;
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;

	@FXML
	private Label labelErrorName;

	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("service was null");
		}
		try {

			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListener();
			Utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		} catch(ValidationException e) {
			setErrorMessages(e.getErrors());
		}
	}

	private void notifyDataChangeListener() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();				
			
		}
		
	}

	private Seller getFormData() {
		Seller obj = new Seller();
		ValidationException exception = new ValidationException("Validation error!");
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			// vazio
			exception.addError("Name", "Field can't be empty");
		}
		obj.setName(txtName.getText());
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		return obj;

	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializableNodes();

	}

	private void initializableNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}

	public void setSeller(Seller entity) {
		this.entity = entity;
	}

	public void setSellerService(SellerService service) {
		this.service = service;
	}
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	public void updateFormData() {
		// programação defensiva
		if (entity == null) {
			throw new IllegalStateException("entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("Name")) {
			labelErrorName.setText(errors.get("Name"));
			
		}
		
	}
}