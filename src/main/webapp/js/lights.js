'use strict';

q("table button").each(function() {
	const picker = new ColorPicker('#'+this.attr('id'), {
		enableAlpha: false,
		defaultFormat: 'hsv',
		submitMode: 'confirm', // 'instant' | 'confirm'
		showClearButton: true,
		dismissOnOutsideClick: true,
		formats: false,
	}).on('pick', (color) => {
		var val = color.string('hsv');
		val = val.replace(/[()%]/g,'');
//		val = val.replace(/,/g,'_');
		q.ajax(this.attr('data-url')+val).process();
	});
});

