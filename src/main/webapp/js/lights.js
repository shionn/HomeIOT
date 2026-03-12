'use strict';

q("table button").each(function() {
	new ColorPicker('#'+this.attr('id'), {
		enableAlpha: false,
		defaultFormat: 'hsv',
		submitMode: 'confirm', // 'instant' | 'confirm'
		showClearButton: true,
		dismissOnOutsideClick: true,
		formats: false,
	}).on('pick', (color) => {
		var val = color.string('hsv');
		val = val.replace(/[()%]/g,'');
		q.ajax(this.attr('data-url')+val).process();
	});
});

q("table select").on("change", function() {
	q.ajax(q(this).attr('data-url')+q(this).value()).process();
});

