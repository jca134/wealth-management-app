(function () {
    var controllers = {};

    function getController(targetSelector) {
        if (!controllers[targetSelector]) {
            var table = document.querySelector(targetSelector);
            if (!table) return null;
            controllers[targetSelector] = {
                table: table,
                rows: Array.prototype.slice.call(table.querySelectorAll('tbody tr')),
                textInputs: [],
                dateRanges: []
            };
        }
        return controllers[targetSelector];
    }

    function applyFilter(targetSelector) {
        var c = controllers[targetSelector];
        if (!c) return;
        c.rows.forEach(function (row) {
            var cells = row.querySelectorAll('td');
            var visible = true;

            for (var t = 0; t < c.textInputs.length && visible; t++) {
                var q = c.textInputs[t].value.trim().toLowerCase();
                if (!q) continue;
                var match = false;
                for (var i = 0; i < cells.length; i++) {
                    if (cells[i].classList.contains('actions')) continue;
                    if (cells[i].textContent.toLowerCase().indexOf(q) !== -1) {
                        match = true;
                        break;
                    }
                }
                if (!match) visible = false;
            }

            for (var r = 0; r < c.dateRanges.length && visible; r++) {
                var range = c.dateRanges[r];
                var from = range.fromInput && range.fromInput.value;
                var to = range.toInput && range.toInput.value;
                if (!from && !to) continue;
                var cell = cells[range.col];
                var raw = cell ? cell.textContent.trim() : '';
                if (!raw) { visible = false; break; }
                if (from && raw < from) { visible = false; break; }
                if (to && raw > to) { visible = false; break; }
            }

            row.style.display = visible ? '' : 'none';
        });
    }

    document.querySelectorAll('input[data-search-target]').forEach(function (input) {
        var target = input.getAttribute('data-search-target');
        var controller = getController(target);
        if (!controller) return;
        var role = input.getAttribute('data-search-role') || 'text';

        if (role === 'date-from' || role === 'date-to') {
            var col = parseInt(input.getAttribute('data-search-col'), 10);
            var range = null;
            for (var i = 0; i < controller.dateRanges.length; i++) {
                if (controller.dateRanges[i].col === col) { range = controller.dateRanges[i]; break; }
            }
            if (!range) {
                range = { col: col, fromInput: null, toInput: null };
                controller.dateRanges.push(range);
            }
            if (role === 'date-from') range.fromInput = input;
            else range.toInput = input;
        } else {
            controller.textInputs.push(input);
        }

        input.addEventListener('input', function () { applyFilter(target); });
        input.addEventListener('change', function () { applyFilter(target); });
    });
})();
