'use strict';

q(function() {
	q("canvas").each(function(canvas) {
		q.ajax(canvas.attr("data-captor")).success(function(result) {
			new Chart(canvas.obj[0], {
				type: 'line',
				data: result.data,
				options: {
					plugins: {
						title: {
							display: true,
							text: result.title
						}
					}
				}
			});
		}).process();
	});
})
