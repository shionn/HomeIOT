'use strict';

q(function() {
	const ctx = q("canvas").obj[0];
	q.ajax("captor/history/100").success(function(result) {
		new Chart(ctx, {
			type: 'line',
			data: result
		});
	}).process();
})
