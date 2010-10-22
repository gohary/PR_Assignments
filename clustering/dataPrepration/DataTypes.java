package dataPrepration;

import utils.Pattern;
import utils.fourDPoint.FourDPoint;

public enum DataTypes {

	BRITISH_TOWNS(FourDPoint.class);

	private DataTypes(Class<? extends Pattern> type) {
		this.type = type;
	}

	private Class<? extends Pattern> getType() {
		return this.type;
	}

	private Class<? extends Pattern> type;
}
