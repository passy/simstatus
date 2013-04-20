test:
	cd app; ant debug install
	cd unit-tests; ant
	cd integration-tests; ant test
