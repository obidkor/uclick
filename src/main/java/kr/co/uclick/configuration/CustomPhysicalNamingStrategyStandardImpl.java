package kr.co.uclick.configuration;

import java.util.Locale;


//테이블,필드명 카멜케이스일때 인식못할경우가 생김(사용자지점 이름 지정전략)
//카멜-->스네이크 케이스로 이름변환 //우리는 최대 절전 모드의 PhysicalNamingStrategyStandardImpl 을 확장
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomPhysicalNamingStrategyStandardImpl extends PhysicalNamingStrategyStandardImpl {
	private static final long serialVersionUID = -6972754781554141247L;

	
	//db 명명 규칙을 적용하기 위해 toPhysicalTableName 및 toPhysicalColumnName 메서드의 기본 동작을 재정의
	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		return new Identifier(addUnderscores(name.getText()), name.isQuoted());
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
		return new Identifier(addUnderscores(name.getText()), name.isQuoted());
	}
	
	//변경방법
	protected static String addUnderscores(String name) {
		final StringBuilder buf = new StringBuilder(name.replace('.', '_'));
		for (int i = 1; i < buf.length() - 1; i++) {
			if (Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i))
					&& Character.isLowerCase(buf.charAt(i + 1))) {
				buf.insert(i++, '_');
			}
		}
		return buf.toString().toLowerCase(Locale.ROOT);////
	}
}
